import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { JefaturasDetailComponent } from './jefaturas-detail.component';

describe('Jefaturas Management Detail Component', () => {
  let comp: JefaturasDetailComponent;
  let fixture: ComponentFixture<JefaturasDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JefaturasDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./jefaturas-detail.component').then(m => m.JefaturasDetailComponent),
              resolve: { jefaturas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(JefaturasDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JefaturasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load jefaturas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', JefaturasDetailComponent);

      // THEN
      expect(instance.jefaturas()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
