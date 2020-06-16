import {ChangeDetectionStrategy, Component, ElementRef, HostListener, Input, OnInit} from '@angular/core';
import {Node} from '../../models/node';
import {ChainService} from '../../services/helpers/chain.service';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'g[app-node]',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class NodeComponent implements OnInit {

  @Input() nodedata: Node;

  constructor(private element: ElementRef,
              private chainService: ChainService) {
  }

  ngOnInit() {
  }

  @HostListener('click', ['$event'])
  private elementClicked($event) {
    $event.preventDefault();
    $event.stopPropagation();
    this.chainService.selection.next({data: this.nodedata, event: $event});
  }

}
