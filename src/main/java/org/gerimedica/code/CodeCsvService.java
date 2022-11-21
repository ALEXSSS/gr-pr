package org.gerimedica.code;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface CodeCsvService {

    List<Code> getAllCodes(InputStream in);

    void writeCodes(OutputStream out, List<Code> codes);
}
