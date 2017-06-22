import java.util.*;

//1  (+)ПРОВЕРКА ПРАВИЛЬНОСТИ ПЕРЕДЕЛАНА
//2  (+)ПЕРЕДЕЛАНА ОБРЕЗКА НЕЗНАЧАЩИХ НУЛЕЙ В НАЧАЛЕ
//3  (+)3 ПУНКТ КАЖЕТСЯ НЕ АКТУАЛЕН,Т.К. ОСТАЛОСЬ ТОЛЬКО ПОЛЕ ArrayList
//4  (+)БУДЕТ ЧИСЛО РАВНОЕ НУЛЮ
//5  (-)В СЛОЖЕНИИ И ВЫЧИТАНИИ ИСПОЛЬЗОВАНЫ КОПИИ,ПЕРЕДЕЛАТЬ,ЧТОБЫ НЕ БЫЛО
//6  (+)ненужный if убран
//7  (-)Разве у ArrayLis есть метод reverse?
public class EDLargeInteger implements Comparable<EDLargeInteger> {

    private ArrayList<Integer> IntArray = new ArrayList<>();


    public EDLargeInteger(String strDigit) {

        int zero = 0;
        for (int i = 0; i < strDigit.length(); i++) {
            if (strDigit.charAt(i) == '0') {
                zero++;
                i++;
            } else break;
        }
        strDigit.substring(0,zero);

        for (int i = 0; i < strDigit.length(); i++) {
            if (Character.isDigit(strDigit.charAt(i)))
                IntArray.add(Character.getNumericValue(strDigit.charAt(i)));
            else throw new IllegalArgumentException();
        }


    }


    public EDLargeInteger(ArrayList<Integer> intArray) {

        while ((intArray.get(0) == 0) && (!intArray.isEmpty())) {
            intArray.remove(0);
            if (intArray.isEmpty()) {
                intArray.add(0);
                break;
            }
        }
        IntArray = new ArrayList<Integer>(intArray);

    }


    public EDLargeInteger() {
        IntArray.add(0);

    }


    //СЛОЖЕНИЕ
    public EDLargeInteger summa(EDLargeInteger str2) {
        ArrayList<Integer>str=IntArray;
        ArrayList<Integer> Rezult = new ArrayList<Integer>();


        int ost = 0;
//Сложение массивов
        for (int i = str.size() - 1; i >= 0; i--) {
            int sum = 0;
            sum = str.get(i) + str2.IntArray.get(i) + ost;
            ost = sum / 10;
            sum = sum % 10;

            Rezult.add(0, sum);

        }
        if (ost != 0) {
            Rezult.add(0, ost);
        }

        return new EDLargeInteger(Rezult);


    }


    //ВЫЧИТАНИЕ
    public EDLargeInteger subtraction(EDLargeInteger str2) {
        ArrayList<Integer> Rezult = new ArrayList<Integer>();
        ArrayList<Integer> str = this.IntArray;

        //Подведение чисел под общий знаменатель
        if (str.size() != str2.IntArray.size()) {

            while (str.size() > str2.IntArray.size()) {
                str2.IntArray.add(0, 0);
            }

            while (str2.IntArray.size() > str.size()) {
                str.add(0, 0);
            }

        }

        for (int i = 0; i < str.size(); i++) {
            if (str.get(i) > str2.IntArray.get(i)) {
                break;
            }
            if (str.get(i) < str2.IntArray.get(i)) {
                return null;
            }
        }

        int debt = 0;
        for (int i = str.size() - 1; i >= 0; i--) {
            int sum = 0;
            if (str.get(i) < str2.IntArray.get(i)) {
                sum = str.get(i) + 10 - str2.IntArray.get(i) - debt;
                debt = 1;
            } else {
                sum = str.get(i) - str2.IntArray.get(i) - debt;
                debt = 0;
            }
            Rezult.add(0, sum);


        }

        return new EDLargeInteger(Rezult);

    }


    //УМНОЖЕНИЕ
    public EDLargeInteger multiply(EDLargeInteger str2) {

        ArrayList[] massSlog = new ArrayList[str2.IntArray.size()];
        for (int i = 0; i < str2.IntArray.size(); i++) {
            massSlog[i] = new ArrayList<Integer>();
        }


//Получаем составные части произведения которые потом нужно сложить

        int proizv;
        int ost = 0;


        for (int i = str2.IntArray.size() - 1; i >= 0; i--) {
            for (int j = IntArray.size() - 1; j >= 0; j--) {
                proizv = IntArray.get(j) * str2.IntArray.get(i) + ost;
                ost = proizv / 10;
                proizv = proizv % 10;
                massSlog[i].add(0, proizv);


            }

            if (ost != 0)
                massSlog[i].add(0, ost);

            int k = str2.IntArray.size() - i - 1;
            while (k > 0) {
                k--;
                massSlog[i].add(0);
            }

            ost = 0;
        }


        EDLargeInteger Rezult = new EDLargeInteger();
        for (int i = 0; i < str2.IntArray.size(); i++) {
            Rezult = Rezult.summa(new EDLargeInteger(massSlog[i]));
        }

        return Rezult;

    }


    //ДЕЛЕНИЕ
    public EDLargeInteger div(EDLargeInteger str2) {
        ArrayList<Integer> Rezult = new ArrayList<Integer>();
        ArrayList<Integer> massStr3 = new ArrayList<Integer>(IntArray);
        ArrayList<Integer> massStr4 = new ArrayList<Integer>(str2.IntArray);
        EDLargeInteger nol = new EDLargeInteger();
        while ((massStr3.size() - massStr4.size()) > 1) {
            massStr4.add(0);
            if ((massStr3.size() - massStr4.size() == 1) && (massStr3.get(0) > massStr4.get(0))) {
                massStr4.add(0);
            }


        }
        EDLargeInteger a = new EDLargeInteger(massStr3);
        EDLargeInteger b = new EDLargeInteger(massStr4);
        if (a.compareTo(b) == -1)
            return new EDLargeInteger("0");
        if (a.compareTo(b) == 0)
            return new EDLargeInteger("1");
        EDLargeInteger boriginal = new EDLargeInteger(str2.IntArray);
        Rezult.add(0, 0);
        while ((!boriginal.equals(b)) || (a.subtraction(b) != null)) {
            if (a.subtraction(b) != null) {
                a = a.subtraction(b);
                Rezult.set(Rezult.size() - 1, Rezult.get(Rezult.size() - 1) + 1);
                for (int j = Rezult.size() - 1; j >= 0; j--) {
                    if (Rezult.get(j) == 10) {
                        if (j == 0) {
                            Rezult.add(0, 1);
                            Rezult.set(1, 0);
                        } else {
                            Rezult.set(j, 0);
                            Rezult.set(j - 1, Rezult.get(j - 1) + 1);
                        }
                    }
                }
            }
            if ((boriginal.equals(b)) && (a.subtraction(b) == null)) {
                return new EDLargeInteger(Rezult);
            }
            if (a.equals(nol)) {
                while (!(massStr4).equals(str2.IntArray)) {
                    Rezult.add(Rezult.size(), 0);
                    massStr4.remove(massStr4.size() - 1);
                }
            } else {
                while ((a.subtraction(b) == null)) {
                    if (massStr4.size() > boriginal.IntArray.size()) {
                        massStr4.remove(massStr4.size() - 1);
                        Rezult.add(Rezult.size(), 0);
                    }
                    b = new EDLargeInteger(massStr4);
                    if ((boriginal.equals(b)) && (a.subtraction(b) == null))
                        return new EDLargeInteger(Rezult);
                }
            }

        }

        return new EDLargeInteger(Rezult);
    }


    //ОСТАТОК ОТ ДЕЛЕНИЯ
    public EDLargeInteger mod(EDLargeInteger divisor) {
        EDLargeInteger delimoe = new EDLargeInteger(IntArray);
        EDLargeInteger chastnoe = new EDLargeInteger(delimoe.div(divisor).toString());
        return delimoe.subtraction(chastnoe.multiply(divisor));
    }


    @Override
    public int compareTo(EDLargeInteger str2) {
        if (IntArray.size() == str2.IntArray.size()) {
            for (int i = 0; i < IntArray.size(); ) {
                if (IntArray.get(i) == str2.IntArray.get(i)) {
                    i++;
                } else {
                    if (IntArray.get(i) > str2.IntArray.get(i)) {
                        return 1;
                    } else if (str2.IntArray.get(i) > IntArray.get(i)) {
                        return -1;
                    }
                }


            }
        } else {
            if (IntArray.size() > str2.IntArray.size())
                return 1;
            else if (str2.IntArray.size() > IntArray.size())
                return -1;

        }
        return 0;
    }


    @Override
    public boolean equals(Object another) {
        if (another instanceof EDLargeInteger) {
            EDLargeInteger anotherED = (EDLargeInteger) another;
            return this.toString().equals(anotherED.toString());
        }
        return false;


    }


    // ВОЗВРАТ БОЛЬШЕГО
    public EDLargeInteger max(EDLargeInteger str2) {
        if (compareTo(str2) == 1)
            return delzero(this);
        else {
            return delzero(str2);
        }


    }


    //ВОЗВРАТ МЕНЬШЕГО
    public EDLargeInteger min(EDLargeInteger str2) {
        EDLargeInteger str = new EDLargeInteger(IntArray);
        if (str.compareTo(str2) == 1)
            return delzero(str2);
        else {
            return delzero(str);
        }

    }


    @Override
    public int hashCode() {

        return IntArray.hashCode();

    }


    private void delete(int number) {
        IntArray.remove(number);
    }


    public String toString() {
        String str = "";
        for (int i = 0; i < IntArray.size(); i++)
            str += IntArray.get(i);
        return str;
    }


    private EDLargeInteger delzero(EDLargeInteger ed) {
        StringBuilder strBuilder = new StringBuilder(ed.toString());
        while ((strBuilder.charAt(0) == '0') && (strBuilder.length() > 1)) {
            strBuilder.deleteCharAt(0);
        }
        ed = new EDLargeInteger(strBuilder.toString());
        return ed;
    }

}
