import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {NodeTypes} from '../../models/nodeTypes';
import {Node} from '../../models/node';
import {Observable} from 'rxjs';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NodesService {

  private base_uri = environment.SERVER_URI + 'api/v1/components';

  constructor(private http: HttpClient) {
  }

  public getAllNodes(): Observable<Node[]> {
    const httpParams: HttpParams = new HttpParams()
      .set('componentTypes', Object.values(NodeTypes).filter(e => typeof e === 'string').join());
    return this.http.get<Node[]>(this.base_uri, {params: httpParams});
  }

  getNodesByType(types: string) {

  }
}
