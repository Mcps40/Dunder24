import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJefaturas } from '../jefaturas.model';
import { JefaturasService } from '../service/jefaturas.service';

const jefaturasResolve = (route: ActivatedRouteSnapshot): Observable<null | IJefaturas> => {
  const id = route.params.id;
  if (id) {
    return inject(JefaturasService)
      .find(id)
      .pipe(
        mergeMap((jefaturas: HttpResponse<IJefaturas>) => {
          if (jefaturas.body) {
            return of(jefaturas.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default jefaturasResolve;
