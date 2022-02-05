# selenium-n11

### case1
n11 sitesi için hazırlanmış örnek case'i içermektedir. Tüm mağazaları gezip alınan mağaza isimleri excel'e aktarılmış ve rastgele seçilen bir mağazanın yorumları kontrol edilmiştir. (exportexcel.java)

### case2
Ürün aratıldıktan sonra 3. sıradaki ürün seçilerek sepete 2 adet eklenmiştir ve sepet kontrolü yapılmıştır. (search.java)

## selenium grid
docker-compose içinde selenium grid servisleri kurulmuştur, ayağa kaldırmak için `docker-compose up -d` yeterlidir. Paralel birden fazla makine ayağa kaldırmak için aşağıdaki komut kullanılabilir:

```
docker-compose up -d --scale chrome=2 --scale edge=2 --scale firefox=2
```

Böylelikle her tarayıcıdan ikişer tane instance açar. Testleri koşmak için java kodu paralel şekilde koşulabilecek şekilde ayarlanmıştır. Intellij idea'a açtıktan sonra örneğin exportexcel.java'nın içine girip main fonksiyonu çalıştırılırsa testler koşmaya başlar.

> NOT: Selenium grid docker ile alakalı bir eksiklikten dolayı M1 çiplerde çalışmamaktadır.

Kodun içersinde de `localhost:4444` üzerinden erişildiği görülmektedir. Bilgisayarınızdaki 4444 portunun kullanılmıyor olması gerekmektedir.
