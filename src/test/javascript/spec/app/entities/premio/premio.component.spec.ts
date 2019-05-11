/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { PremioComponent } from 'app/entities/premio/premio.component';
import { PremioService } from 'app/entities/premio/premio.service';
import { Premio } from 'app/shared/model/premio.model';

describe('Component Tests', () => {
  describe('Premio Management Component', () => {
    let comp: PremioComponent;
    let fixture: ComponentFixture<PremioComponent>;
    let service: PremioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [PremioComponent],
        providers: []
      })
        .overrideTemplate(PremioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PremioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PremioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Premio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.premios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
