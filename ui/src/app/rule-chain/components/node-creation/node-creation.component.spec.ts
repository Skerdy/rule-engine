import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeCreationComponent } from './node-creation.component';

describe('NodeCreationComponent', () => {
  let component: NodeCreationComponent;
  let fixture: ComponentFixture<NodeCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NodeCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NodeCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
