
# Car Make & Angle Classification 🚗🔍

This project focuses on developing deep learning models to classify car makes and detect the angle of the car in an image. Using a combination of convolutional neural networks and data preprocessing techniques, the project aims to accurately predict both **the make (manufacturer)** and **viewing angle** of cars from images.

The repository contains two main Jupyter notebooks, each handling a specific part of the pipeline.

## Project Structure

```
📦 Car-Make-Classification
├── DL_Project Angle Detection.ipynb
├── DL_Project Angle based classification models.ipynb
├── README.md
└── (Your dataset and model checkpoints - not included here)
```

## Notebooks Overview

### 1. DL_Project Angle Detection.ipynb
- **Purpose**: Detects the angle (front, rear, side, etc.) of a car from an image.
- **Contents**:
  - Data loading and preprocessing
  - Data augmentation techniques
  - Model architecture using Convolutional Neural Networks (CNNs)
  - Model training and evaluation
  - Angle classification results and accuracy metrics

### 2. DL_Project Angle based classification models.ipynb
- **Purpose**: Builds car make classification models that utilize the angle of the car for more accurate predictions.
- **Contents**:
  - Uses angle detection results to enhance make classification
  - Model training for car make prediction
  - Evaluation of angle-based classification models
  - Analysis of accuracy improvements when combining angle data

## Features

- 📊 **Angle-based classification:** Improve prediction accuracy by incorporating car angle information.
- 🧩 **Custom CNN models:** Tailored deep learning architectures for image classification.
- 🖼️ **Data augmentation:** Robust training with various augmentation techniques.
- 📈 **Evaluation metrics:** Accuracy scores and loss tracking for both models.

## Installation & Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/cnikesh/Car-Make-Classification.git
   cd Car-Make-Classification
   ```

2. Install dependencies (recommended: use a virtual environment):
   ```bash
   pip install -r requirements.txt
   ```

3. Add your dataset in the project directory (make sure to update the paths in the notebooks accordingly).

4. Run the notebooks step by step to train and evaluate the models.

## Requirements

- Python 3.7+
- TensorFlow / Keras
- NumPy
- Pandas
- Matplotlib
- Scikit-learn
- (Add any additional dependencies in `requirements.txt`)

## Results

- Achieved high accuracy for both angle detection and make classification models.
- Angle-based approach demonstrated improved classification performance over baseline.

## Future Work

- 📦 Integrate a pre-trained model for faster and more accurate results.
- 🖥️ Deploy the models as an API or web service.
- 🚗 Expand dataset to include more car makes and angles.
- 📊 Add confusion matrix and ROC curve visualizations.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests.


## Author

**Nikesh Chithambaram**  
[LinkedIn](https://www.linkedin.com/in/nikesh-chithambaram/) • [GitHub](https://github.com/cnikesh)
