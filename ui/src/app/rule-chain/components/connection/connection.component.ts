import {Component, ElementRef, HostListener, Input, OnInit} from '@angular/core';
import {Connection} from '../../models/connection';
import {ChainService} from '../../services/helpers/chain.service';

@Component({
  selector: 'g[app-connection]',
  templateUrl: './connection.component.html',
  styleUrls: ['./connection.component.scss']
})
export class ConnectionComponent implements OnInit {

  @Input() line: Connection;

  constructor(private element: ElementRef,
              private chainService: ChainService) { }

  ngOnInit() {
  }

  @HostListener('click', ['$event'])
  private onClick($event) {
    $event.preventDefault();
    $event.stopPropagation();
    this.chainService.selection.next({data: this.line, event: $event});
  }

}
