import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeVisualizationComponent } from './node-visualization.component';

describe('NodeVisualizationComponent', () => {
  let component: NodeVisualizationComponent;
  let fixture: ComponentFixture<NodeVisualizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NodeVisualizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NodeVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
