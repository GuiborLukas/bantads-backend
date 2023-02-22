import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, retry, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      retry(1),
      catchError((e: HttpErrorResponse) => {
        let errorMessage = '';
        if (e.error instanceof ErrorEvent) {
          //Client-side error
          errorMessage = `Erro: ${e.error.message}`;
        }
        else {
          //Server-side error
          errorMessage = `Código: ${e.status} \nMensagem: ${e.message}`;
        }
        window.alert(errorMessage);
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
