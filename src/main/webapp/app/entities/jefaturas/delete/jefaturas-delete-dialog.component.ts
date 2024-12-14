import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IJefaturas } from '../jefaturas.model';
import { JefaturasService } from '../service/jefaturas.service';

@Component({
  standalone: true,
  templateUrl: './jefaturas-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class JefaturasDeleteDialogComponent {
  jefaturas?: IJefaturas;

  protected jefaturasService = inject(JefaturasService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jefaturasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
