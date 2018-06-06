# pmock

好用的mock框架，无侵入、零学习成本，case通过脚本语言配置。

mock example:

 PersonBusinessService personBusinessService =
                mockObject(PersonBusinessDao.class).
                        mockTarget(PersonBusinessServiceImpl.class).
                        mockObject(PlayRpc.class).
                        mockField("playRpc").
                        mockField("personBusinessDao").target();
                        
1 var personBusinessService is tested class object.

2 function mockTarget is uesed to instantiation the tested class: PersonBusinessService.

3 funtion mockObject is used to mock the Oject that mockTarget dependent on ,funtion mockField is used to establish mockObject.

4 groovy tested cas,queryShopping is the function of mockObject ,paraObj is input parameter of mockField,returned json data is reponse parameter of mockField.

case config example:

def queryShopping(paraObj) {

    if (paraObj.name == 'test')
        return "{'person':{'name':'shopping'}}"
}
