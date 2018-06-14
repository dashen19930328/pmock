import groovy.json.JsonSlurper

//mock condition
def queryPersonMap(paraObj) {
    if (paraObj.name == 'test')
        return "{'aop':{'name':'map aop'}}"
}

def queryShopping(paraObj) {
    if (paraObj.name == 'test')
        return "{'person':{'name':'shopping'}}"
}

def queryShoppingResult(paraObj) {
    if (paraObj.name == 'test')
        return "{'desc':'查询血拼','flag':true,'value':{'desc':'血拼的人','person':{'name':'泛型result'}}}"
}


def queryPersonList(personQuery) {
    if (personQuery==null)
        return "[{'name':'Jim','age':15,'sex':1},{'name':'Tom','age':5,'sex':1},{'name':'Mike','age':12,'sex':1},{'name':'Bruce','age':28,'sex':1},{'name':'Lily','age':13,'sex':1},{'name':'Hair','age':15,'sex':1}]";
    else if (  personQuery.name == 'test')
        return "[{'name':'list'}]";
    else   if (personQuery.sex == 1)
        return "[{'name':'Jim','age':15,'sex':1},{'name':'Tom','age':5,'sex':1},{'name':'Mike','age':12,'sex':1},{'name':'Bruce','age':28,'sex':1},{'name':'Lily','age':13,'sex':1},{'name':'Hair','age':15,'sex':1}]";
}
//纯粹为了做复杂的输入条件测试
def queryComplexShopping(shoppingResult, query) {
    if (shoppingResult!=null && query!=null){
        if(query.sex==1){
            if (shoppingResult.value.desc=='test'){
                if (shoppingResult.value.person.age>1){
                    return "{'name':'test123','age':789}"
                }
            }
        }
    }
}

def queryString(string){
    println string;
    return "queryString";
}


def queryCount(count){
    println count;
    return "9";
}

def queryCountInter(count){
    println count;
    return "9";
}

def queryAge(paraObj) {
    if (paraObj.name == 'queryAge')
        return "{'name':'test123','age':789}"
}

static   main(def args){
    def jsonSlurper = new JsonSlurper();
    def map = jsonSlurper.parseText('{"name":"queryAge"}');
    println queryAge(map) ;
    println queryString('ss');
}