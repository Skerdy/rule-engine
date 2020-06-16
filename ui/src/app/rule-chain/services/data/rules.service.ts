import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Rule} from '../../models/rule';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RulesService {

  private base_uri = environment.SERVER_URI + '/api/v1/rules';

  constructor(private http: HttpClient) {
  }

  getChains(): Observable<Rule[]> {
    return this.http.get<Rule[]>(this.base_uri);
  }

  getById(id: string): Observable<Rule> {
    return this.http.get<Rule>(this.base_uri + `/${id}`);
  }

  createChain(rule: Rule): Observable<any> {
    return this.http.post<any>(this.base_uri, rule);
  }

  updateChain(rule: Rule): Observable<any> {
    return this.http.put<any>(this.base_uri, rule);
  }

  deleteChain(rule: Rule): Observable<any> {
    return this.http.delete(this.base_uri + `/${rule.id}`);
  }
}
