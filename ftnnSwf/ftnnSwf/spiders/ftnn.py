import scrapy
import urllib.request
import socket

swfSrcPage = ['http://www.4399.com/flash/70846_4.htm']
storagePath = "D://software projects/Python/4399-swf-extractor/Flash Memory/Flash Games/"


class FtnnSpider(scrapy.Spider):
    name = 'ftnn'
    start_urls = swfSrcPage

    def parse(self, response):
        link = response.xpath('//script[@language="javascript"]')[0].re('_strGamePath(.+)')
        link = link[0].replace('="','').replace('";\r','')
        title = response.xpath('//script[@language="javascript"]')[0].re('title=\'(.+)\';')[0]
        link = 'http://sda.4399.com/4399swf' + link
        global storagePath
        filename = 'result.json'
        storedFile = '{}{}.swf'.format(storagePath, title)
        with open(filename, 'w',encoding='utf-8') as file_object:
            file_object.write("{\"link\": " + "\"" + link + "\",\"title\": \""+title+"\",\"storedLocation\":\""+storedFile+"\"},")
        urllib.request.urlretrieve(link, storagePath + title + '.swf')
