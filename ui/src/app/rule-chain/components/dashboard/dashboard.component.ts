import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Node} from '../../models/node';
import {Connection} from '../../models/connection';
import {SVGService} from '../../services/helpers/svg.service';
import {ChainService} from '../../services/helpers/chain.service';
import {NodesService} from '../../services/data/nodes.service';
import {RulesService} from '../../services/data/rules.service';
import {fromEvent, merge, Observable, of, zip} from 'rxjs';
import {filter, flatMap, groupBy, map, mergeMap, takeUntil, tap, toArray} from 'rxjs/operators';
import {Rule} from '../../models/rule';
import {MatDialog} from '@angular/material';
import {NodeDataComponent} from '../node-data/node-data.component';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {


  public nodes: Node[];
  public lines1: Connection[] = [];
  public elements: Node[] = [];
  public clusteredNodes: any[];
  private _chain: Rule;
  private selectedElements: (Connection | Node)[];
  @ViewChild('deletebutton') private el: ElementRef;

  private deleteClicked$ = new Observable();

  constructor(private svgService: SVGService,
              private chainService: ChainService,
              private nodesService: NodesService,
              private ruleService: RulesService,
              private _dialog: MatDialog,
              private _route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // this.svgService.initializePanZoom();
    this.getAllNodes();
    this.setData();
    this.chainService.start();
    this.subscribe();
    this.deleteClicked$ = fromEvent(this.el.nativeElement, 'click');

  }

  public getAllNodes() {
    this.nodesService.getAllNodes()
      .pipe(tap(e => this.nodes = e))
      .pipe(flatMap(e => e))
      .pipe(groupBy(e => e.type))
      .pipe(mergeMap(group => zip(of(group.key), group.pipe(toArray()))))
      .pipe(toArray())
      .pipe(tap(e => this.clusteredNodes = e))
      .subscribe();
  }

  onDelete() {
    return merge(this.chainService.clickDropZone$, this.deleteClicked$)
      .pipe(tap(e => console.log(e)));
  }

  public generateLines(lines: any) {
    of(lines)
      .pipe(flatMap(e => e))
      .pipe(map(e => this.setPositions(e)))
      .pipe(toArray())
      .subscribe(e => this.lines1 = e);
  }

  public saveChain() {
    this._chain.nodeDocuments = this.elements;
    this._chain.nodeConnectionInfos = this.lines1;
    this.ruleService.updateChain(this._chain)
      .subscribe(e => console.log(e),
        error => console.log(error));
  }

  ngOnDestroy(): void {
    this.chainService.unsuscribeAll.next();
  }

  deleteElements() {
    this.elements = this.elements.filter(e => !(this.selectedElements.indexOf(e) >= 0));
    this.lines1 = this.lines1.filter(e => !(this.selectedElements.indexOf(e) >= 0));
    this.selectedElements = [];
  }

  updateElement() {
    this._dialog.open(NodeDataComponent, {
      data: <Node>this.selectedElements[0]
    }).afterClosed().subscribe(e => {
      if (e) {
        this.selectedElements[0] = e;
      }
    });
  }

  private setPositions(e: any) {
    e.d = this.chainService.calculateFullLine(this.getStartPoint(e.fromIndex), this.getEndPoint(e.toIndex));
    return e;
  }

  private subscribe() {
    merge(this.subscribeToNewElement(),
      this.subscribeToElementDragging(),
      this.onSelectedElements(),
      this.onDelete(),
      this.subscribeToLine())
      .pipe(takeUntil(this.chainService.unsuscribeAll))
      .subscribe();
  }

  private subscribeToNewElement(): Observable<any> {
    return this.chainService.elementDroped
      .pipe(filter(e => e.name))
      .pipe(tap(e => this.onElementDroped(e)));
  }

  private onElementDroped(element: Node) {
    element.nodeLayout.id = this.elements.length;
    this._dialog.open(NodeDataComponent, {
      data: element
    }).afterClosed().subscribe(result => {
      if (result) {
        this.elements.push(element);
      }
    });
  }

  private getStartPoint(start: any) {
    return this.elements
      .filter(e => e.nodeLayout.id === start)
      .map(e => {
        return {x: e.nodeLayout.x, y: e.nodeLayout.y};
      })[0];
  }

  private getEndPoint(end: any) {
    return this.elements
      .filter(e => e.nodeLayout.id === end)
      .map(e => {
        return {x: e.nodeLayout.x + 5, y: e.nodeLayout.y + 25};
      })[0];
  }

  private subscribeToElementDragging() {
    return this.chainService.elementDragging
      .pipe(tap(element => this.updateLine(element)));
  }

  private updateLine(element: Node) {
    this.lines1.filter(line => line.fromIndex === element.nodeLayout.id || line.toIndex === element.nodeLayout.id)
      .map(trueline => {
        trueline.d = trueline.fromIndex === element.nodeLayout.id ?
          this.chainService.calculateLineStart(trueline.d, element.nodeLayout) :
          this.chainService.calculateLineEnd(trueline.d, element.nodeLayout);
      });
  }

  private subscribeToLine(): Observable<any> {
    const a = this.chainService.lineCreated.pipe(tap(e => this.lines1.push(e)));
    const b = this.chainService.removeline.pipe(tap(e => this.lines1.pop()));
    return merge(a, b);
  }

  private setData() {
    this._route.params
      .pipe(flatMap(e => this.ruleService.getById(e.id)))
      .pipe(tap(e => console.log(e)))
      .pipe(tap(e => this.setElements(e)))
      .subscribe();
  }

  private setElements(rule: Rule) {
    this._chain = rule;
    this.elements = rule.nodeDocuments;
    this.generateLines(rule.nodeConnectionInfos);
  }

  private onSelectedElements() {
    return merge(this.chainService.onSelection(), this.chainService.cancelAction$)
      .pipe(tap(elements => this.selectedElements = elements));
  }
}
