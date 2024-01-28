package com.wpbrewery.mms.walterpenk.services;

import com.wpbrewery.mms.walterpenk.model.BeerCsvRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCsvService {
    List<BeerCsvRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
