import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Node} from '../../models/node';
import {of} from 'rxjs';
import {filter, flatMap, map, tap, toArray} from 'rxjs/operators';
import {DynamicFormModel, DynamicFormService, DynamicInputModel} from '@ng-dynamic-forms/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-node-data',
  templateUrl: './node-data.component.html',
  styleUrls: ['./node-data.component.scss']
})
export class NodeDataComponent implements OnInit {

  formModel: DynamicFormModel = [];
  formGroup: FormGroup = new FormGroup({});


  constructor(private _dialogRef: MatDialogRef<NodeDataComponent>,
              @Inject(MAT_DIALOG_DATA) private _data: Node,
              private formService: DynamicFormService) {
  }

  ngOnInit(): void {
    this.createFormGroup();
  }

  public close(result: any) {
    if (result) {
      this._data.configurationProperties = this.formGroup.getRawValue();
    }
    this._dialogRef.close(result ? this._data : false);
  }

  private createFormGroup() {
    console.log(this._data);
    of(this._data).pipe(
      filter(e => e.configurationFields && e.configurationFields.length > 0),
      map(e => e.configurationFields),
      flatMap(e => e),
      map(e => this.toInputModel(e)),
      toArray(),
      tap(e => this.formModel = e),
      tap(e => this.formGroup = this.formService.createFormGroup(this.formModel))
    ).subscribe();
  }

  private toInputModel(element: any) {
    return new DynamicInputModel({
      id: element.name,
      label: element.name,
      maxLength: 420,
      placeholder: element.name,
      value: this._data.configurationProperties ? this._data.configurationProperties[element.name] : '',
    });
  }
}
