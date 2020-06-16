import {Directive, ElementRef, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appDraggable]'
})
export class DraggableDirective {

  @Input() data: any;

  constructor(private el: ElementRef) {
    this.el.nativeElement.setAttribute('draggable', 'true');
  }

  @HostListener('dragstart', ['$event'])
  onDragStart(event) {
    event.dataTransfer.setData('text/plain', JSON.stringify(this.data));
  }

  @HostListener('document:dragover', ['$event'])
  onDragOver(event) {
    event.preventDefault();
  }
}
