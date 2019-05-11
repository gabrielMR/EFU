/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { NiustaDetailComponent } from 'app/entities/niusta/niusta-detail.component';
import { Niusta } from 'app/shared/model/niusta.model';

describe('Component Tests', () => {
  describe('Niusta Management Detail Component', () => {
    let comp: NiustaDetailComponent;
    let fixture: ComponentFixture<NiustaDetailComponent>;
    const route = ({ data: of({ niusta: new Niusta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [NiustaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NiustaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NiustaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.niusta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
