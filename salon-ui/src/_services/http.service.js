import { messageService } from '.';

// https://javascript.info/fetch-progress
// https://dev.to/tqbit/how-to-monitor-the-progress-of-a-javascript-fetch-request-and-cancel-it-on-demand-107f
// https://www.npmjs.com/package/fetch-

const complete = 100;

async function _fetchWithProgress (url, init = {}) {
  let response = await fetch(url, init);

  const reader = response.body.getReader();

  // approximate progress counter percentage in reverse
  let remainingContent = 75;

  // read the data
  let receivedLength = 0;
  let chunks = [];
  while (true) {
    const { done, value } = await reader.read();

    if (done) {
      messageService.sendPercentageComplete(receivedLength === 0  ? 0 : complete);
      break;
    }

    messageService.sendPercentageComplete(complete - remainingContent);

    chunks.push(value);
    receivedLength += value.length;
    remainingContent -= remainingContent > 25 ? remainingContent - 25 : 0;
  }

  // concatenate chunks
  let allChunks = new Uint8Array(receivedLength);
  let position = 0;
  for (let chunk of chunks) {
    allChunks.set(chunk, position);
    position += chunk.length;
  }

  // decode into string
  let result = new TextDecoder('utf-8').decode(allChunks);

  return JSON.parse(result);
}

export function fetchWithProgress (url) {
  return _fetchWithProgress(url);
}

export function postWithProgress (url, data = {}) {
  return _fetchWithProgress(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
}

export function putWithProgress (url, data = {}) {
  return _fetchWithProgress(url, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
}
