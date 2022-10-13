# -*- coding: UTF-8 -*-
import requests
import re


path = ""
url = 'https://netcut.cn/video414'



filename = path.split('/')[-1]
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36',
    'referer': 'https://netcut.cn/'
}
note_name = url.split('cn/')[1]
response = requests.get(url, headers=headers)
data_id = re.compile(r'data-id="(?P<data_id>.*?)"', re.S).search(response.text).group('data_id')


url = 'https://v0.api.upyun.com/netcut-cn'
data = {
    'authorization': 'UPYUN netcut:+n7US6H37TPG9Vn5wyXRK9KWF4I=',
    'policy': 'eyJzYXZlLWtleSI6InVwbG9hZHMve3llYXJ9e21vbn17ZGF5fS97cmFuZG9tMzJ9IiwiZXhwaXJhdGlvbiI6MTcyMTMwMzMwNywiY29udGVudC1sZW5ndGgtcmFuZ2VcdCI6IjAsNTI0Mjg4MDAiLCJidWNrZXQiOiJuZXRjdXQtY24ifQ=='
}
response = requests.post(url, headers=headers, data=data, files={"file": open(path, "rb")})
print(response.text)
file_url = response.json()['url']


url = 'https://netcut.cn/api/upload/file/'
data = {
    'note_name': note_name,
    'note_id': data_id,
    'file_size': '46182954',
    'file_name': filename,
    'file_url': file_url
}
response = requests.post(url, headers=headers, data=data)
print(response.json())

