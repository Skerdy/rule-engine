import {ElementRef, Injectable} from '@angular/core';
import {fromEvent, merge, Observable, Subject} from 'rxjs';
import {filter, map, mergeMap, takeUntil, tap} from 'rxjs/operators';
import {SVGService} from './svg.service';
import {Connection} from '../../models/connection';
import {Node} from '../../models/node';

@Injectable({
  providedIn: 'root'
})
export class ChainService {
  public dragstart: Subject<any> = new Subject();
  public drag: Subject<any> = new Subject();
  public dragend: Subject<any> = new Subject();
  public drop: Subject<any> = new Subject();
  public linestart: Subject<any> = new Subject();
  public lineend: Subject<any> = new Subject();
  public lineupdate: Subject<any> = new Subject();
  public linedrop: Subject<any> = new Subject();
  public selection: Subject<{ data: Connection | Node, event: any }> = new Subject();

  clickDropZone$ = new Observable();

  public elementDroped: Subject<any> = new Subject();
  public elementDragging: Subject<any> = new Subject();
  public lineCreated: Subject<any> = new Subject();
  public removeline: Subject<any> = new Subject();
  public unsuscribeAll: Subject<any> = new Subject();
  public cancelAction$: Observable<any> = new Observable();
  private fromDropZone$: Observable<any> = new Observable();
  private dropzone: ElementRef;
  private selectedElement: any = null;
  private offset: any = null;
  private line;
  private selectedElements: { data: Connection | Node, event: any }[] = [];

  constructor(private svgService: SVGService) {
  }

  public setDropzone(dropzone: any) {
    this.dropzone = dropzone;
    this.fromDropZone$ = fromEvent(this.dropzone.nativeElement, 'mousemove');
    this.cancelAction$ = fromEvent(this.dropzone.nativeElement, 'click');
    this.dragTest().subscribe();
    this.onCancelAction().subscribe();
    this.clickDropZone$ = fromEvent(this.dropzone.nativeElement, 'keypress'); // .pipe(filter(e => e.key === 'Delete'));
  }

  public start() {
    merge(this.setupDragStart(),
      this.setupDrag(),
      this.setupDragEnd(),
      this.setupDrop(),
      this.setupLineEnd(),
      this.setupLineUpdate(),
      this.setupLineDrop(),
      this.dragTest(),
      this.lineDrawTest(),
      this.setupLineStart())
      .pipe(takeUntil(this.unsuscribeAll))
      .subscribe();
  }

  calculateFullLine(start: { x: number, y: number }, end: { x: number, y: number }) {
    return `M ${start.x + 170} ${start.y + 25} L ${end.x} ${end.y}`;
  }

  calculateLineStart(old: string, start: { x: number, y: number }) {
    const a = old.split('L');
    a[0] = 'M ' + (start.x + 170) + ' ' + (start.y + 25);
    return a.join(' L ');
  }

  calculateLineEnd(old: string, end: { x: number, y: number }) {
    const a = old.split('L');
    a[1] = (end.x + 3) + ' ' + (end.y + 25);
    return a.join('L ');
  }

  calculateLineUpdate(old: string, end: { x: number, y: number }) {
    const a = old.split('L');
    a[1] = (end.x) + ' ' + (end.y);
    return a.join('L ');
  }

  public onSelection() {
    return this.selection
      .pipe(tap(e => {
        e.event.target.classList.toggle('selected');
        if (!e.event.ctrlKey && this.selectedElements.length > 0) {
          if (!(this.selectedElements.length === 1 && this.selectedElements[0].data === e.data)) {
            console.log('errererere');
            this.unSelectAll();
          }
        }
        const idx = this.selectedElements.map(a => a.data).indexOf(e.data);
        if (idx >= 0) {
          this.selectedElements.splice(idx, 1);
        } else {
          this.selectedElements.push(e);
        }
        console.log(this.selectedElements);
      }))
      .pipe(map(e => this.selectedElements.map(c => c.data)));
  }

  private dragTest(): Observable<any> {
    return this.dragstart.asObservable()
      .pipe(mergeMap(dragStart => this.fromDropZone$.pipe(takeUntil(this.dragend.asObservable()))))
      .pipe(tap(e => this.drag.next(e)));
  }

  private lineDrawTest(): Observable<any> {
    return this.linestart.asObservable()
      .pipe(mergeMap(dragStart => this.fromDropZone$.pipe(takeUntil(this.lineend.asObservable()))))
      .pipe(tap(e => this.lineupdate.next(e)));
  }

  private onCancelAction() {
    return this.cancelAction$
      .pipe(tap(e => this.unSelectAll()))
      .pipe(map(e => []));
  }

  private setupLineStart() {
    return this.linestart
      .pipe(tap(event => {
          if (this.line) {
            this.removeline.next(event);
            this.line = null;
          } else {
            const start = {x: event.element.nodeLayout.x, y: event.element.nodeLayout.y};
            const end = this.svgService.getSVGPoint(event.evt, this.dropzone.nativeElement);
            this.line = {
              fromIndex: event.element.nodeLayout.id,
              d: this.calculateFullLine(start, end)
            };
            this.lineCreated.next(this.line);
          }
        }
      ));
  }

  private setupLineEnd() {
    return this.lineend
      .pipe(filter(e => this.line != null))
      .pipe(tap(endElement => {
        if (this.line.fromIndex === endElement.nodeLayout.id) {
          this.removeline.next(endElement);
          this.line = null;
        } else {
          const end = {x: endElement.nodeLayout.x, y: endElement.nodeLayout.y};
          this.line.toIndex = endElement.nodeLayout.id;
          this.line.d = this.calculateLineEnd(this.line.d, end);
          this.line = null;
        }
      }));
  }

  private setupLineUpdate() {
    return this.lineupdate
      .pipe(filter(e => this.line != null))
      .pipe(tap(event => {
          this.line.d = this.calculateLineUpdate(this.line.d, this.svgService.getSVGPoint(event, this.dropzone.nativeElement));
        }
      ));
  }

  private setupDragStart() {
    return this.dragstart
      .pipe(tap(event => {
        this.selectedElement = event.data;
        this.offset = this.calculateOffset(event.evt, event.element);
      }));
  }

  private setupDrop() {
    return this.drop
      .pipe(tap(event => {
        const point = this.svgService.getSVGPoint(event.evt, this.dropzone.nativeElement);
        const data = event.data;
        data.nodeLayout = {x: point.x, y: point.y};
        this.elementDroped.next(data);
        this.selectedElement = null;
        this.offset = null;
      }));
  }

  private setupDrag() {
    return this.drag
      .pipe(filter(e => this.selectedElement != null))
      .pipe(tap(event => {
        const point = this.svgService.getSVGPoint(event, this.dropzone.nativeElement);
        this.selectedElement.nodeLayout.x = point.x - this.offset.x;
        this.selectedElement.nodeLayout.y = point.y - this.offset.y;
        this.elementDragging.next(this.selectedElement);
      }));
  }

  private setupDragEnd() {
    return this.dragend
      .pipe(tap(e => {
        this.selectedElement = null;
        this.offset = null;
      }));
  }

  private setupLineDrop() {
    return this.linedrop
      .pipe(filter(e => this.line != null))
      .pipe(tap(event => {
        this.line = null;
        this.removeline.next();
      }));
  }

  private unSelectAll() {
    this.selectedElements = this.selectedElements
      .map(a => a.event.target.classList.toggle('selected'))
      .filter(c => false);
  }

  private calculateOffset($event: any, node: any) {
    const offset = this.svgService.getSVGPoint($event, this.dropzone.nativeElement);
    offset.x -= parseFloat(node.getAttributeNS(null, 'x'));
    offset.y -= parseFloat(node.getAttributeNS(null, 'y'));
    return offset;
  }
}
