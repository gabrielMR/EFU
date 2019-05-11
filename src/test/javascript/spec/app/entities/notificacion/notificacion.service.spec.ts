/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NotificacionService } from 'app/entities/notificacion/notificacion.service';
import { INotificacion, Notificacion } from 'app/shared/model/notificacion.model';

describe('Service Tests', () => {
  describe('Notificacion Service', () => {
    let injector: TestBed;
    let service: NotificacionService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificacion;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NotificacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Notificacion(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Notificacion', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            hora: currentDate
          },
          returnedFromService
        );
        service
          .create(new Notificacion(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Notificacion', async () => {
        const returnedFromService = Object.assign(
          {
            tituloNotificacion: 'BBBBBB',
            descripcion: 'BBBBBB',
            contenido: 'BBBBBB',
            estado: 1,
            fecha: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT),
            idFraternidad: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            hora: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Notificacion', async () => {
        const returnedFromService = Object.assign(
          {
            tituloNotificacion: 'BBBBBB',
            descripcion: 'BBBBBB',
            contenido: 'BBBBBB',
            estado: 1,
            fecha: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT),
            idFraternidad: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            hora: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Notificacion', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
