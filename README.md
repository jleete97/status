## Status Display Application

Small application meant for running on a small single-board computer,
such as a Raspberry Pi, and displaying a web page with general household
status info (today's events and weather, calendar, etc.).

Much of the functionality will be in the web page itself. It will gather
weather and other data on its own (possibly with some input from the
server app, such as location), and will occasionally update to match
events. (The HTTP 2 standard should help with this sort of push
notification, I think.) Other pages will be available for very basic
event management, through a servlet that looks a bit like a REST
service.

The service layer will be light: servlets, Jackson to help output
JSON to the front end, and a file-based storage system. Not pretty,
but light.
