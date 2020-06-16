import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Rule} from '../../models/rule';
import {RulesService} from '../../services/data/rules.service';

@Component({
  selector: 'app-node-creation',
  templateUrl: './node-creation.component.html',
  styleUrls: ['./node-creation.component.scss']
})
export class NodeCreationComponent implements OnInit {

  chainForm: FormGroup;

  constructor(private _dialogRef: MatDialogRef<NodeCreationComponent>,
              private _ruleService: RulesService,
              @Inject(MAT_DIALOG_DATA) private _data: Rule) {
  }

  ngOnInit() {
    this.initData();
    this.initForm();
  }

  close(result?: boolean) {
    this._dialogRef.close(result);
  }

  saveChain() {
    this._ruleService.createChain(this.toRuleChain()).subscribe(e => this.close(true));
  }

  private initData() {
    if (!this._data.id) {
      this._data = {
        name: '',
        description: '',
        nodeDocuments: [],
        nodeConnectionInfos: [],
        started: false
      };
    }
  }

  private initForm() {
    this.chainForm = new FormGroup({
      'name': new FormControl(this._data.name, Validators.required),
      'description': new FormControl(this._data.name, Validators.required)
    });
  }

  private toRuleChain() {
    const data = this.chainForm.getRawValue();
    this._data.name = data.name;
    this._data.description = data.description;
    return this._data;
  }
}
