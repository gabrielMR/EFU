import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFraterno } from 'app/shared/model/fraterno.model';

type EntityResponseType = HttpResponse<IFraterno>;
type EntityArrayResponseType = HttpResponse<IFraterno[]>;

@Injectable({ providedIn: 'root' })
export class FraternoService {
  public resourceUrl = SERVER_API_URL + 'api/fraternos';

  constructor(protected http: HttpClient) {}

  create(fraterno: IFraterno): Observable<EntityResponseType> {
    return this.http.post<IFraterno>(this.resourceUrl, fraterno, { observe: 'response' });
  }

  update(fraterno: IFraterno): Observable<EntityResponseType> {
    return this.http.put<IFraterno>(this.resourceUrl, fraterno, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFraterno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFraterno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
