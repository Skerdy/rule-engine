import {Directive, ElementRef, HostListener, Input} from '@angular/core';
import {ChainService} from '../services/helpers/chain.service';

@Directive({
  selector: '[appLinestart]'
})
export class LinestartDirective {
  @Input() nodedata: any;

  constructor(private element: ElementRef,
              private chainService: ChainService) {
  }

  @HostListener('mouseover', ['$event'])
  onMouseOver(event): void {
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('mouseout', ['$event'])
  onMouseOut(event): void {
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('mousedown', ['$event'])
  onMouseDown(event): void {
    event.preventDefault();
    event.stopPropagation();
    this.chainService.linestart.next({element: this.nodedata, evt: event});
  }
}
