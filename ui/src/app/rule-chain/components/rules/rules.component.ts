import {Component, OnInit} from '@angular/core';
import {RulesService} from '../../services/data/rules.service';
import {Rule} from '../../models/rule';
import {flatMap, groupBy, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
import {NodeCreationComponent} from '../node-creation/node-creation.component';
import {ControlService} from '../../services/data/control.service';

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss']
})
export class RulesComponent implements OnInit {

  public rules: Rule[] = [];

  constructor(private rulesService: RulesService,
              private controlService: ControlService,
              private router: Router,
              private _dialog: MatDialog) {
  }

  ngOnInit() {
    this.getRules();
  }

  createChain(rule?: Rule) {
    this._dialog.open(NodeCreationComponent, {data: rule ? rule : {}}).afterClosed()
      .subscribe(e => {
        if (e) {
          this.getRules();
        }
      });
  }

  deleteChain(rule: Rule) {
    this.rulesService.deleteChain(rule)
      .subscribe(e => this.getRules(),
        error => console.log(error));
  }

  private getRules() {
    this.rulesService.getChains()
      .pipe(tap(rules => this.rules = rules))
      .subscribe();
  }

  startChain(chain: Rule) {
    this.controlService.start(chain.id)
      .subscribe(e => this.getRules(),
        error => console.log(error));
  }

  stopChain(chain: Rule) {
    this.controlService.stop(chain.id)
      .subscribe(e => this.getRules(),
        error => console.log(error));
  }

  restartChain(chain: Rule) {
    this.controlService.restart(chain.id)
      .subscribe(e => this.getRules(),
        error => console.log(error));

  }
}
