// src/main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Angular Material modules you need
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

bootstrapApplication(App, {
  providers: [
    importProvidersFrom(
      HttpClientModule,
      ReactiveFormsModule,
      BrowserAnimationsModule,
      MatInputModule,
      MatSelectModule,
      MatButtonModule,
      MatCardModule,
      MatIconModule
    )
  ]
}).catch(err => console.error(err));