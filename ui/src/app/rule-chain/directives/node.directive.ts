import {Directive, ElementRef, HostListener, Input} from '@angular/core';
import {ChainService} from '../services/helpers/chain.service';

@Directive({
  selector: '[appNode]'
})
export class NodeDirective {

  @Input() nodedata: any;

  constructor(private element: ElementRef,
              private chainService: ChainService) {
  }

  @HostListener('mousedown', ['$event'])
  onMouseDown($event) {
    event.preventDefault();
    event.stopPropagation();
    this.chainService.dragstart.next({evt: $event, element: this.element.nativeElement, data: this.nodedata});
    this.element.nativeElement.style.cursor = 'grabbing';
  }

  @HostListener('mousemove', ['$event'])
  onMouseMove($event) {

  }

  @HostListener('mouseup', ['$event'])
  onMouseUp($event) {
    event.preventDefault();
    event.stopPropagation();
    this.chainService.dragend.next($event);
    this.element.nativeElement.style.cursor = 'default';
  }

  @HostListener('mouseleave', ['$event'])
  onMouseLeave($event) {
    // event.preventDefault();
    // event.stopPropagation();
    // this.chainService.d     ragend.next($event);
  }
}
