/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FraternidadService } from 'app/entities/fraternidad/fraternidad.service';
import { IFraternidad, Fraternidad } from 'app/shared/model/fraternidad.model';

describe('Service Tests', () => {
  describe('Fraternidad Service', () => {
    let injector: TestBed;
    let service: FraternidadService;
    let httpMock: HttpTestingController;
    let elemDefault: IFraternidad;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FraternidadService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Fraternidad(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fundacion: currentDate.format(DATE_FORMAT)
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

      it('should create a Fraternidad', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fundacion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Fraternidad(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Fraternidad', async () => {
        const returnedFromService = Object.assign(
          {
            idFraternidad: 1,
            nombre: 'BBBBBB',
            danza: 'BBBBBB',
            instancia: 'BBBBBB',
            fundacion: currentDate.format(DATE_FORMAT),
            descripcion: 'BBBBBB',
            estado: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fundacion: currentDate
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

      it('should return a list of Fraternidad', async () => {
        const returnedFromService = Object.assign(
          {
            idFraternidad: 1,
            nombre: 'BBBBBB',
            danza: 'BBBBBB',
            instancia: 'BBBBBB',
            fundacion: currentDate.format(DATE_FORMAT),
            descripcion: 'BBBBBB',
            estado: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacion: currentDate
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

      it('should delete a Fraternidad', async () => {
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
