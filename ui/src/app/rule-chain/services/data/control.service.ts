import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ControlService {

  private base_uri = environment.SERVER_URI + 'api/v1/rule/control';

  constructor(private http: HttpClient) {
  }


  public start(id: string) {
    return this.http.post(this.base_uri + `/start/${id}`, {});
  }

  public stop(id: string) {
    return this.http.post(this.base_uri + `/stop/${id}`, {});
  }


  public restart(id: string) {
    return this.http.post(this.base_uri + `/restart/${id}`, {});
  }
}
