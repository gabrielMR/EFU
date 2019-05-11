import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComunicado } from 'app/shared/model/comunicado.model';

type EntityResponseType = HttpResponse<IComunicado>;
type EntityArrayResponseType = HttpResponse<IComunicado[]>;

@Injectable({ providedIn: 'root' })
export class ComunicadoService {
  public resourceUrl = SERVER_API_URL + 'api/comunicados';

  constructor(protected http: HttpClient) {}

  create(comunicado: IComunicado): Observable<EntityResponseType> {
    return this.http.post<IComunicado>(this.resourceUrl, comunicado, { observe: 'response' });
  }

  update(comunicado: IComunicado): Observable<EntityResponseType> {
    return this.http.put<IComunicado>(this.resourceUrl, comunicado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComunicado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComunicado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
