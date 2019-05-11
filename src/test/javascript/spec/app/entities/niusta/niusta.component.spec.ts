/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { NiustaComponent } from 'app/entities/niusta/niusta.component';
import { NiustaService } from 'app/entities/niusta/niusta.service';
import { Niusta } from 'app/shared/model/niusta.model';

describe('Component Tests', () => {
  describe('Niusta Management Component', () => {
    let comp: NiustaComponent;
    let fixture: ComponentFixture<NiustaComponent>;
    let service: NiustaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [NiustaComponent],
        providers: []
      })
        .overrideTemplate(NiustaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NiustaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NiustaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Niusta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.niustas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
