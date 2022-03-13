package com.starry.starryapi.service.spiMode.dataDictionary;

public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Override
    public boolean addDictionary(String key, String value) {
        System.out.println("add" + key + "..." + value);
        return false;
    }
}
