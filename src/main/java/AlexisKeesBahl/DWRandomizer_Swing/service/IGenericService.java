package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.model.*;

import java.util.Scanner;

public interface IGenericService<T extends IPWClass>  {
    String showOptions(Scanner dataInput,Class<T> parameterClass);
}
