import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-node-visualization',
  templateUrl: './node-visualization.component.html',
  styleUrls: ['./node-visualization.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NodeVisualizationComponent implements OnInit {

  @Input() nodedata: Node;

  constructor() {
  }

  ngOnInit() {
  }

}
