/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;

/**
 *
 * @author 0201sosay
 */
public abstract class Element implements Jouable {

    public int x;
    public int y;

    public abstract Type getType();
}
