import { CalendarEventTitleFormatter, CalendarEvent } from 'angular-calendar';

/**
 * format the description
 */
export class CustomEventTitleFormatter extends CalendarEventTitleFormatter {
  constructor() {
    super();
  }

  locale: string = 'de';

  month(event: CalendarEvent): string {
    return `<div class="black"><b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      year: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      year: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b><br>${event.title}</div>`;
  }

  week(event: CalendarEvent): string {
    return `<b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b><br>${event.title}`;
  }

  day(event: CalendarEvent): string {
    return `<b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b><br>${event.title}}`;
  }
}