function todayDateAs_yyyy_MM_dd() {
  function pad(s) { return (s < 10) ? '0' + s : s; }
  const d = new Date();
  return [d.getFullYear(), pad(d.getMonth()+1), pad(d.getDate())].join('-');
}

export {
  todayDateAs_yyyy_MM_dd
}
