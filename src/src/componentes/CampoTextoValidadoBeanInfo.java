package componentes;

import java.beans.*;

public class CampoTextoValidadoBeanInfo extends SimpleBeanInfo{

    @Override
    public PropertyDescriptor[] getPropertyDescriptors(){

        try{

            PropertyDescriptor tipo =
                    new PropertyDescriptor(
                            "tipoValidacion",
                            CampoTextoValidado.class);

            tipo.setDisplayName("Tipo Validación");

            return new PropertyDescriptor[]{
                tipo
            };

        }catch(IntrospectionException e){

            return null;

        }

    }

}