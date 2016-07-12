/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katadatamunging;

import java.io.BufferedReader;
import java.io.IOException;

public interface IDataLogic {
    void processHeader(BufferedReader br) throws IOException;
    Integer provideDiff(String line);
}
