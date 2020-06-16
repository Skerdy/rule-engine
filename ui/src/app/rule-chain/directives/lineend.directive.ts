import {Directive, ElementRef, HostListener, Input} from '@angular/core';
import {ChainService} from '../services/helpers/chain.service';

@Directive({
  selector: '[appLineEnd]'
})
export class LineendDirective {
  @Input() nodedata: any;

  constructor(private element: ElementRef,
              private chainService: ChainService) {
  }

  @HostListener('mouseover', ['$event'])
  onMouseOver(event): void {
        // this.element.nativeElement.classList.add('hover');
  }

  @HostListener('mouseout', ['$event'])
  onMouseOut(event): void {
    // this.element.nativeElement.classList.remove('hover');
  }

  @HostListener('mouseup', ['$event'])
  onClick(event): void {
    this.chainService.lineend.next(this.nodedata);
  }
}
