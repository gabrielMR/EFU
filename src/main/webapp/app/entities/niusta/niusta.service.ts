import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INiusta } from 'app/shared/model/niusta.model';

type EntityResponseType = HttpResponse<INiusta>;
type EntityArrayResponseType = HttpResponse<INiusta[]>;

@Injectable({ providedIn: 'root' })
export class NiustaService {
  public resourceUrl = SERVER_API_URL + 'api/niustas';

  constructor(protected http: HttpClient) {}

  create(niusta: INiusta): Observable<EntityResponseType> {
    return this.http.post<INiusta>(this.resourceUrl, niusta, { observe: 'response' });
  }

  update(niusta: INiusta): Observable<EntityResponseType> {
    return this.http.put<INiusta>(this.resourceUrl, niusta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INiusta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INiusta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
