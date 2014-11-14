
package cguide.db.beans;

import java.util.Comparator;


/**
 * Comparator class is used to sort the AutenticacaoBean objects.
 * @author sql2java
 */
public class AutenticacaoComparator implements Comparator
{
    /**
     * Holds the field on which the comparison is performed.
     */
    private int iType;
    /**
     * Value that will contain the information about the order of the sort: normal or reversal.
     */
    private boolean bReverse;

    /**
     * Constructor class for AutenticacaoComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new AutenticacaoComparator(AutenticacaoManager.ID_DURACAO, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>AutenticacaoManager.ID_DURACAO
     *   <li>AutenticacaoManager.ID_AUTH
     *   <li>AutenticacaoManager.ID_UTILIZADOR_ID
     *   <li>AutenticacaoManager.ID_ID
     * </ul>
     */
    public AutenticacaoComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for AutenticacaoComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new AutenticacaoComparator(AutenticacaoManager.ID_DURACAO, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>AutenticacaoManager.ID_DURACAO
     *   <li>AutenticacaoManager.ID_AUTH
     *   <li>AutenticacaoManager.ID_UTILIZADOR_ID
     *   <li>AutenticacaoManager.ID_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public AutenticacaoComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    @Override
	public int compare(Object pObj1, Object pObj2)
    {
        AutenticacaoBean b1 = (AutenticacaoBean)pObj1;
        AutenticacaoBean b2 = (AutenticacaoBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case AutenticacaoManager.ID_DURACAO:
                if (b1.getDuracao() == null && b2.getDuracao() != null) {
                    iReturn = -1;
                } else if (b1.getDuracao() == null && b2.getDuracao() == null) {
                    iReturn = 0;
                } else if (b1.getDuracao() != null && b2.getDuracao() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getDuracao().compareTo(b2.getDuracao());
                }
                break;
            case AutenticacaoManager.ID_AUTH:
                if (b1.getAuth() == null && b2.getAuth() != null) {
                    iReturn = -1;
                } else if (b1.getAuth() == null && b2.getAuth() == null) {
                    iReturn = 0;
                } else if (b1.getAuth() != null && b2.getAuth() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getAuth().compareTo(b2.getAuth());
                }
                break;
            case AutenticacaoManager.ID_UTILIZADOR_ID:
                if (b1.getUtilizadorId() == null && b2.getUtilizadorId() != null) {
                    iReturn = -1;
                } else if (b1.getUtilizadorId() == null && b2.getUtilizadorId() == null) {
                    iReturn = 0;
                } else if (b1.getUtilizadorId() != null && b2.getUtilizadorId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getUtilizadorId().compareTo(b2.getUtilizadorId());
                }
                break;
            case AutenticacaoManager.ID_ID:
                if (b1.getId() == null && b2.getId() != null) {
                    iReturn = -1;
                } else if (b1.getId() == null && b2.getId() == null) {
                    iReturn = 0;
                } else if (b1.getId() != null && b2.getId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getId().compareTo(b2.getId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}
