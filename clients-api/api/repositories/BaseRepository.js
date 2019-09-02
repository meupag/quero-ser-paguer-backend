
class BaseRepository{
    constructor(model){
        this.model = model;
    }

    findOrCreate(data){
        return this.model.findOrCreate(data)
    }
    read(fiter){
        
    }
    update(){

    }
    delete(){

    }
}

module.exports = BaseRepository;