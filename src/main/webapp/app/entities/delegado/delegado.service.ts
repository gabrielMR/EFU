import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDelegado } from 'app/shared/model/delegado.model';

type EntityResponseType = HttpResponse<IDelegado>;
type EntityArrayResponseType = HttpResponse<IDelegado[]>;

@Injectable({ providedIn: 'root' })
export class DelegadoService {
  public resourceUrl = SERVER_API_URL + 'api/delegados';

  constructor(protected http: HttpClient) {}

  create(delegado: IDelegado): Observable<EntityResponseType> {
    return this.http.post<IDelegado>(this.resourceUrl, delegado, { observe: 'response' });
  }

  update(delegado: IDelegado): Observable<EntityResponseType> {
    return this.http.put<IDelegado>(this.resourceUrl, delegado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDelegado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDelegado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
