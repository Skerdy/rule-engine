import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {RuleChainRoutingModule} from './rule-chain-routing.module';
import {DraggableDirective} from './directives/draggable.directive';
import {DroppableDirective} from './directives/droppable.directive';
import {LinestartDirective} from './directives/linestart.directive';
import {LineendDirective} from './directives/lineend.directive';
import {NodeDirective} from './directives/node.directive';
import {NodeComponent} from './components/node/node.component';
import {ConnectionComponent} from './components/connection/connection.component';
import {NodeVisualizationComponent} from './components/node-visualization/node-visualization.component';
import {RulesComponent} from './components/rules/rules.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {MaterialModule} from './material/material.module';
import {NodeDataComponent} from './components/node-data/node-data.component';
import {NodeCreationComponent} from './components/node-creation/node-creation.component';
import {DynamicFormsCoreModule} from '@ng-dynamic-forms/core';
import {DynamicFormsMaterialUIModule} from '@ng-dynamic-forms/ui-material';

@NgModule({
  declarations: [
    DraggableDirective,
    DroppableDirective,
    LinestartDirective,
    LineendDirective,
    NodeDirective,
    NodeComponent,
    ConnectionComponent,
    NodeVisualizationComponent,
    RulesComponent,
    DashboardComponent,
    NodeDataComponent,
    NodeCreationComponent
  ],
  imports: [
    CommonModule,
    RuleChainRoutingModule,
    MaterialModule,
    DynamicFormsCoreModule,
    DynamicFormsMaterialUIModule
  ],
  entryComponents: [
    NodeDataComponent,
    NodeCreationComponent
  ]

})
export class RuleChainModule {
}
