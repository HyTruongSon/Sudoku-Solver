// Software: Sudoku Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright (c) 2015. All rights reserved.

package logic;

import java.util.ArrayList;

public class SudokuSolver {
    
    private int nTableRows, nTableColumns, nCellRows, nCellColumns;
    private int currentTable[][];
    private int cellIndex[][];
    private boolean rowCover[][];
    private boolean columnCover[][];
    private boolean cellCover[][];
    private int nCells;
    private int rangeNumber;
    private boolean isEmpty[][];
    private ArrayList<Integer> result = null;
    private boolean foundSolution;
    
    public SudokuSolver(int nTableRows, int nTableColumns, int nCellRows, int nCellColumns) {
        this.nTableRows = nTableRows;
        this.nTableColumns = nTableColumns;
        this.nCellRows = nCellRows;
        this.nCellColumns = nCellColumns;
        
        rangeNumber = this.nCellRows * this.nCellColumns;
        
        currentTable = new int [this.nTableRows][this.nTableColumns];
        isEmpty = new boolean [this.nTableRows][this.nTableColumns];
        for (int i = 0; i < this.nTableRows; ++i) {
            for (int j = 0; j < this.nTableColumns; ++j) {
                currentTable[i][j] = 0;
            }
        }
        
        cellIndex = new int [this.nTableRows][this.nTableColumns];
        nCells = 0;
        for (int i = 0; i < this.nTableRows / this.nCellRows; ++i) {
            for (int j = 0; j < this.nTableColumns / this.nCellColumns; ++j) {
                int x0 = i * this.nCellRows;
                int y0 = j * this.nCellColumns;
                for (int x = 0; x < this.nCellRows; ++x) {
                    for (int y = 0; y < this.nCellColumns; ++y) {
                        cellIndex[x0 + x][y0 + y] = nCells;
                    }
                }
                ++nCells;
            }
        }
        
        rowCover = new boolean [this.nTableRows][rangeNumber + 1];
        columnCover = new boolean [this.nTableColumns][rangeNumber + 1];
        cellCover = new boolean [nCells][rangeNumber + 1];
        
        for (int number = 0; number <= rangeNumber; ++number) {
            for (int rowIndex = 0; rowIndex < this.nTableRows; ++rowIndex) {
                rowCover[rowIndex][number] = false;
            }
            for (int columnIndex = 0; columnIndex < this.nTableColumns; ++columnIndex) {
                columnCover[columnIndex][number] = false;
            }
            for (int cellIndex = 0; cellIndex < nCells; ++cellIndex) {
                cellCover[cellIndex][number] = false;
            }
        }
        
        /*
        for (int i = 0; i < this.nTableRows; ++i) {
            for (int j = 0; j < this.nTableColumns; ++j) {
                System.out.print(areaIndex[i][j] + " ");
            }
            System.out.println();
        }
        */
    }
    
    public int getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= nTableRows) || (columnIndex < 0) || (columnIndex >= nTableColumns)) {
            return -1;
        }
        return currentTable[rowIndex][columnIndex];
    }
    
    
    public void setValueAt(int rowIndex, int columnIndex, int newValue) {
        if ((rowIndex < 0) || (rowIndex >= nTableRows) || (columnIndex < 0) || (columnIndex >= nTableColumns)) {
            return;
        }
        if ((newValue < 0) || (newValue > rangeNumber)) {
            return;
        }
        if (rowCover[rowIndex][newValue]) {
            return;
        }
        if (columnCover[columnIndex][newValue]) {
            return;
        }
        if (cellCover[cellIndex[rowIndex][columnIndex]][newValue]) {
            return;
        }
        
        int oldValue = currentTable[rowIndex][columnIndex];
        rowCover[rowIndex][oldValue] = false;
        columnCover[columnIndex][oldValue] = false;
        cellCover[cellIndex[rowIndex][columnIndex]][oldValue] = false;
        
        currentTable[rowIndex][columnIndex] = newValue;
        
        if (newValue == 0) {
            return;
        }
        
        rowCover[rowIndex][newValue] = true;
        columnCover[columnIndex][newValue] = true;
        cellCover[cellIndex[rowIndex][columnIndex]][newValue] = true;
    }
    
    public void setNextValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= nTableRows) || (columnIndex < 0) || (columnIndex >= nTableColumns)) {
            return;
        }
        int oldValue = currentTable[rowIndex][columnIndex];
        for (int increase = 1; increase <= rangeNumber; ++increase) {
            int nextValue = (oldValue + increase) % (rangeNumber + 1);
            
            if (rowCover[rowIndex][nextValue]) {
                continue;
            }
            if (columnCover[columnIndex][nextValue]) {
                continue;
            }
            if (cellCover[cellIndex[rowIndex][columnIndex]][nextValue]) {
                continue;
            }
            
            rowCover[rowIndex][oldValue] = false;
            columnCover[columnIndex][oldValue] = false;
            cellCover[cellIndex[rowIndex][columnIndex]][oldValue] = false;
            
            currentTable[rowIndex][columnIndex] = nextValue;
            
            if (nextValue == 0) {
                return;
            }
            
            rowCover[rowIndex][nextValue] = true;
            columnCover[columnIndex][nextValue] = true;
            cellCover[cellIndex[rowIndex][columnIndex]][nextValue] = true;
            
            return;
        }
    }
    
    private void updateSolution() {
        foundSolution = true;
        result = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                if (isEmpty[rowIndex][columnIndex]) {
                    result.add(currentTable[rowIndex][columnIndex]);
                } else {
                    result.add(-currentTable[rowIndex][columnIndex]);
                }
            }
        }
    }
    
    private void BackTracking(int rowIndex, int columnIndex) {
        if (!isEmpty[rowIndex][columnIndex]) {
            if (columnIndex + 1 < nTableColumns) {
                BackTracking(rowIndex, columnIndex + 1);
            } else 
                if (rowIndex + 1 < nTableRows) {
                    BackTracking(rowIndex + 1, 0);
                } else {
                    updateSolution();
                }
            return;
        }
        
        for (int number = 1; number <= rangeNumber; ++number) {
            if (rowCover[rowIndex][number]) {
                continue;
            }
            if (columnCover[columnIndex][number]) {
                continue;
            }
            if (cellCover[cellIndex[rowIndex][columnIndex]][number]) {
                continue;
            }
            
            rowCover[rowIndex][number] = true;
            columnCover[columnIndex][number] = true;
            cellCover[cellIndex[rowIndex][columnIndex]][number] = true;
            currentTable[rowIndex][columnIndex] = number;
            
            if (columnIndex + 1 < nTableColumns) {
                BackTracking(rowIndex, columnIndex + 1);
            } else 
                if (rowIndex + 1 < nTableRows) {
                    BackTracking(rowIndex + 1, 0);
                } else {
                    updateSolution();
                }
            
            rowCover[rowIndex][number] = false;
            columnCover[columnIndex][number] = false;
            cellCover[cellIndex[rowIndex][columnIndex]][number] = false;
            currentTable[rowIndex][columnIndex] = 0;
            
            if (foundSolution) {
                return;
            }
        }
    }
    
    public ArrayList<Integer> findSolution() {
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                if (currentTable[rowIndex][columnIndex] > 0) {
                    isEmpty[rowIndex][columnIndex] = false;
                } else {
                    isEmpty[rowIndex][columnIndex] = true;
                }
            }
        }
        result = null;
        foundSolution = false;
        BackTracking(0, 0);
        return result;
    }
}
