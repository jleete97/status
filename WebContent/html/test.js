function parseISODate(s) {
  var b = s.split(/\D/);
  var d = new Date();
  d.setHours(0,0,0,0);
  d.setFullYear(b[0], --b[1], b[2]);
  return d.getFullYear() == b[0] && d.getDate() == b[2]? d : NaN;
}