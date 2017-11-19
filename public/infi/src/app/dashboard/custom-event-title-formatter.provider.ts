import { CalendarEventTitleFormatter, CalendarEvent } from 'angular-calendar';

export class CustomEventTitleFormatter extends CalendarEventTitleFormatter {
  constructor() {
    super();
  }

  locale: string = 'de';

  month(event: CalendarEvent): string {
    return `<b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b>${event.title}`;
  }

  week(event: CalendarEvent): string {
    return `<b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b>${event.title}`;
  }

  day(event: CalendarEvent): string {
    return `<b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.start)}</b> - <b>${new Intl.DateTimeFormat(this.locale, {
      hour: 'numeric',
      minute: 'numeric'
    }).format(event.end)} </b>${event.title}}`;
  }
}
