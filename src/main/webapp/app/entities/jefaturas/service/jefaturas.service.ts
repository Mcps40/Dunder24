import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJefaturas, NewJefaturas } from '../jefaturas.model';

export type PartialUpdateJefaturas = Partial<IJefaturas> & Pick<IJefaturas, 'id'>;

export type EntityResponseType = HttpResponse<IJefaturas>;
export type EntityArrayResponseType = HttpResponse<IJefaturas[]>;

@Injectable({ providedIn: 'root' })
export class JefaturasService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jefaturas');

  create(jefaturas: NewJefaturas): Observable<EntityResponseType> {
    return this.http.post<IJefaturas>(this.resourceUrl, jefaturas, { observe: 'response' });
  }

  update(jefaturas: IJefaturas): Observable<EntityResponseType> {
    return this.http.put<IJefaturas>(`${this.resourceUrl}/${this.getJefaturasIdentifier(jefaturas)}`, jefaturas, { observe: 'response' });
  }

  partialUpdate(jefaturas: PartialUpdateJefaturas): Observable<EntityResponseType> {
    return this.http.patch<IJefaturas>(`${this.resourceUrl}/${this.getJefaturasIdentifier(jefaturas)}`, jefaturas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJefaturas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJefaturas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJefaturasIdentifier(jefaturas: Pick<IJefaturas, 'id'>): number {
    return jefaturas.id;
  }

  compareJefaturas(o1: Pick<IJefaturas, 'id'> | null, o2: Pick<IJefaturas, 'id'> | null): boolean {
    return o1 && o2 ? this.getJefaturasIdentifier(o1) === this.getJefaturasIdentifier(o2) : o1 === o2;
  }

  addJefaturasToCollectionIfMissing<Type extends Pick<IJefaturas, 'id'>>(
    jefaturasCollection: Type[],
    ...jefaturasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jefaturas: Type[] = jefaturasToCheck.filter(isPresent);
    if (jefaturas.length > 0) {
      const jefaturasCollectionIdentifiers = jefaturasCollection.map(jefaturasItem => this.getJefaturasIdentifier(jefaturasItem));
      const jefaturasToAdd = jefaturas.filter(jefaturasItem => {
        const jefaturasIdentifier = this.getJefaturasIdentifier(jefaturasItem);
        if (jefaturasCollectionIdentifiers.includes(jefaturasIdentifier)) {
          return false;
        }
        jefaturasCollectionIdentifiers.push(jefaturasIdentifier);
        return true;
      });
      return [...jefaturasToAdd, ...jefaturasCollection];
    }
    return jefaturasCollection;
  }
}
