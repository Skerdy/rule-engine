import {Directive, ElementRef, HostListener} from '@angular/core';
import {SVGService} from '../services/helpers/svg.service';
import {ChainService} from '../services/helpers/chain.service';

@Directive({
  selector: '[appDroppable]'
})
export class DroppableDirective {

  constructor(
    private dropzone: ElementRef,
    private svgService: SVGService,
    private chainService: ChainService) {

    this.chainService.setDropzone(this.dropzone);
  }

  @HostListener('drop', ['$event'])
  onDrop(event) {
    const data = event.dataTransfer.getData('text/plain');
    let e;
    try {
      e = JSON.parse(data);
      this.chainService.drop.next({evt: event, data: e});
    } catch (e) {
      // console.log(e);
    }
  }

  @HostListener('mousemove', ['$event'])
  onMouseOver(event) {
    // this.chainService.lineupdate.next(event);
  }

  @HostListener('mousedown', ['$event'])
  onMouseDown(event) {
    this.chainService.linedrop.next(event);
  }

  @HostListener('mouseout', ['$event'])
  onMouseUp(event) {
    // this.chainService.linedrop.next(event);
  }

  @HostListener('click', ['$event'])
  onClick(event) {
    this.chainService.linedrop.next(event);
  }

}
